var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var searchRouter = require('./routes/search');
var singlePageRouter = require('./routes/single-page');
var navbarRouter = require('./routes/navbar');

var app = express();

// Needed for middleware res locals
const { genreOptionsNavbar, studioOptions, sortOptions } = require('./utils/movieFilters');

const swaggerUi = require('swagger-ui-express');
const openApiDocumentation = require('./swagger/swaggerDocumentation.json');
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(openApiDocumentation));

// view engine setup
app.set('views', path.join(__dirname, 'views'));

const expressHbs = require('express-handlebars');
const hbs = expressHbs.create({
  extname: '.hbs',
  defaultLayout: 'layout',
  layoutsDir: path.join(__dirname, 'views/layouts'),
  partialsDir: path.join(__dirname, 'views/partials'),
  runtimeOptions: {
    allowProtoPropertiesByDefault: true,
    allowProtoMethodsByDefault: true
  },
  helpers: {
    range: function(start, end) {
      const result = [];
      for (let i = start; i <= end; i++) {
        result.push(i);
      }
      return result;
    },
    eq: function(a, b) {
      return a === b;
    },
    add: (a, b) => Number(a) + Number(b),
    subtract: (a, b) => Number(a) - Number(b),
    ifCond: function (v1, operator, v2, options) {
      switch (operator) {
        case '==': return (v1 == v2) ? options.fn(this) : options.inverse(this);
        case '!=': return (v1 != v2) ? options.fn(this) : options.inverse(this);
        case '!==': return (v1 !== v2) ? options.fn(this) : options.inverse(this);
        case '<': return (v1 < v2) ? options.fn(this) : options.inverse(this);
        case '<=': return (v1 <= v2) ? options.fn(this) : options.inverse(this);
        case '>': return (v1 > v2) ? options.fn(this) : options.inverse(this);
        case '>=': return (v1 >= v2) ? options.fn(this) : options.inverse(this);
        case '&&': return (v1 && v2) ? options.fn(this) : options.inverse(this);
        case '||': return (v1 || v2) ? options.fn(this) : options.inverse(this);
        default: return options.inverse(this);
      }
    },
    formatDate: (isoDate) => {
      const date = new Date(isoDate);
      const day = String(date.getDate()).padStart(2, '0');
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const year = date.getFullYear();
      return `${day}-${month}-${year}`;
    },
    getSortLabel: (sortValue) => {
      const found = sortOptions.find(opt => opt.value === sortValue);
      return found ? found.label : sortValue;
    }
  }
});
app.engine('hbs', hbs.engine);
app.set('view engine', 'hbs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));


// Middleware to set up options for genres and studios
app.use((req, res, next) => {
  res.locals.genreOptionsNavbar = genreOptionsNavbar;
  res.locals.studioOptions = studioOptions;
  next();
});



app.use('/', indexRouter);
app.use('/', singlePageRouter);
app.use('/', navbarRouter);
app.use('/', searchRouter);


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('pages/error');
});

module.exports = app;
