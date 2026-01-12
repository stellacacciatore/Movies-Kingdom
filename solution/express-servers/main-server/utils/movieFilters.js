const springBootServerUrl = 'http://localhost:8080';
const mongoDBServerUrl = 'http://localhost:3001';

const genreOptions = [
    {value: 'null', label: 'All'},
    {value: 'genre,Drama', label: 'Drama'},
    {value: 'genre,Documentary', label: 'Documentary'},
    {value: 'genre,Comedy', label: 'Comedy'},
    {value: 'genre,Animation', label: 'Animation'},
    {value: 'genre,Horror', label: 'Horror'},
    {value: 'genre,Romance', label: 'Romance'},
    {value: 'genre,Action', label: 'Action'},
    {value: 'genre,Adventure', label: 'Adventure'},
    {value: 'genre,War', label: 'War'},
    {value: 'genre,Western', label: 'Western'},
    {value: 'genre,Thriller', label: 'Thriller'},
    {value: 'genre,Fantasy', label: 'Fantasy'},
    {value: 'genre,Science Fiction', label: 'Science Fiction'},
    {value: 'genre,Family', label: 'Family'},
    {value: 'genre,Music', label: 'Music'},
    {value: 'genre,History', label: 'History'},
    {value: 'genre,Crime', label: 'Crime'},
    {value: 'genre,Mystery', label: 'Mystery'},

];
const genreOptionsNavbar = [
    {value: 'drama', label: 'Drama'},
    {value: 'romance', label: 'Romance'},
    {value: 'comedy', label: 'Comedy'},
    {value: 'animation', label: 'Animation'},
    {value: 'family', label: 'Family'},
    {value: 'horror', label: 'Horror'},
    {value: 'action', label: 'Action'},
    {value: 'adventure', label: 'Adventure'},
    {value: 'war', label: 'War'},
    {value: 'western', label: 'Western'},
    {value: 'thriller', label: 'Thriller'},
    {value: 'fantasy', label: 'Fantasy'},
    {value: 'science fiction', label: 'Science Fiction'},
    {value: 'documentary', label: 'Documentary'},
    {value: 'music', label: 'Music'},
    {value: 'history', label: 'History'},
    {value: 'crime', label: 'Crime'},
    {value: 'mystery', label: 'Mystery'},
];
const yearOptions = [
    {value: 'null', label: 'All'},
    {value: 'year,2023', label: '2023'},
    {value: 'year,2022', label: '2022'},
    {value: 'year,2021', label: '2021'},
    {value: 'year,2020', label: '2020'},
    {value: 'year,2019', label: '2019'},
    {value: 'year,2018', label: '2018'},
    {value: 'year,2017', label: '2017'},
    {value: 'year,2016', label: '2016'},
    {value: 'year,2015', label: '2015'},
    {value: 'year,2014', label: '2014'},
    {value: 'year,2013', label: '2013'},
    {value: 'year,2012', label: '2012'},
    {value: 'year,2011', label: '2011'},
    {value: 'year,2010', label: '2010'},
    {value: 'year,2009', label: '2009'},
    {value: 'year,2008', label: '2008'},
    {value: 'year,2007', label: '2007'},
    {value: 'year,2006', label: '2006'},
    {value: 'year,2005', label: '2005'},
    {value: 'year,2004', label: '2004'},
    {value: 'year,2003', label: '2003'},
    {value: 'year,2002', label: '2002'},
    {value: 'year,2001', label: '2001'},
    {value: 'year,2000', label: '2000'},
    {value: 'year,1999', label: '1999'},
    {value: 'year,1998', label: '1998'},
    {value: 'year,1997', label: '1997'},
    {value: 'year,1996', label: '1996'},
    {value: 'year,1995', label: '1995'},
    {value: 'year,1994', label: '1994'},
    {value: 'year,1993', label: '1993'},
    {value: 'year,1992', label: '1992'},
    {value: 'year,1991', label: '1991'},
    {value: 'year,1990', label: '1990'},
    {value: 'year,1989', label: '1989'},
    {value: 'year,1988', label: '1988'},
    {value: 'year,1987', label: '1987'},
    {value: 'year,1986', label: '1986'},
    {value: 'year,1985', label: '1985'},
    {value: 'year,1984', label: '1984'},
    {value: 'year,1983', label: '1983'},
    {value: 'year,1982', label: '1982'},
    {value: 'year,1981', label: '1981'},
    {value: 'year,1980', label: '1980'},
    {value: 'year,1979', label: '1979'},
    {value: 'year,1978', label: '1978'},
    {value: 'year,1977', label: '1977'},
    {value: 'year,1976', label: '1976'},
    {value: 'year,1975', label: '1975'},
    {value: 'year,1974', label: '1974'},
    {value: 'year,1973', label: '1973'},
    {value: 'year,1972', label: '1972'},
    {value: 'year,1971', label: '1971'},
    {value: 'year,1970', label: '1970'},
    {value: 'year,1969', label: '1969'},
    {value: 'year,1968', label: '1968'},
    {value: 'year,1967', label: '1967'},
    {value: 'year,1966', label: '1966'},
    {value: 'year,1965', label: '1965'},
    {value: 'year,1964', label: '1964'},
    {value: 'year,1963', label: '1963'},
    {value: 'year,1962', label: '1962'},
    {value: 'year,1961', label: '1961'},
    {value: 'year,1960', label: '1960'},
    {value: 'year,1959', label: '1959'},
    {value: 'year,1958', label: '1958'},
    {value: 'year,1957', label: '1957'},
    {value: 'year,1956', label: '1956'},
    {value: 'year,1955', label: '1955'},
    {value: 'year,1954', label: '1954'},
    {value: 'year,1953', label: '1953'},
    {value: 'year,1952', label: '1952'},
    {value: 'year,1951', label: '1951'},
    {value: 'year,1950', label: '1950'},
    {value: 'year,1949', label: '1949'},
    {value: 'year,1948', label: '1948'},
    {value: 'year,1947', label: '1947'},
    {value: 'year,1946', label: '1946'},
    {value: 'year,1945', label: '1945'},
    {value: 'year,1944', label: '1944'},
    {value: 'year,1943', label: '1943'},
    {value: 'year,1942', label: '1942'},
    {value: 'year,1941', label: '1941'},
    {value: 'year,1940', label: '1940'},
    {value: 'year,1939', label: '1939'},
    {value: 'year,1938', label: '1938'},
    {value: 'year,1937', label: '1937'},
    {value: 'year,1936', label: '1936'},
    {value: 'year,1935', label: '1935'},
    {value: 'year,1934', label: '1934'},
    {value: 'year,1933', label: '1933'},
    {value: 'year,1932', label: '1932'},
    {value: 'year,1931', label: '1931'},
    {value: 'year,1930', label: '1930'},
    {value: 'year,1929', label: '1929'},
    {value: 'year,1928', label: '1928'},
    {value: 'year,1927', label: '1927'},
    {value: 'year,1926', label: '1926'},
    {value: 'year,1925', label: '1925'},
    {value: 'year,1924', label: '1924'},
    {value: 'year,1923', label: '1923'},
    {value: 'year,1922', label: '1922'},
    {value: 'year,1921', label: '1921'},
    {value: 'year,1920', label: '1920'}
];

const languagesOptions = [
    {value: 'null', label: 'All'},
    {value: 'language,English', label: 'English'},
    {value: 'language,Spanish', label: 'Spanish'},
    {value: 'language,French', label: 'French'},
    {value: 'language,Japanese', label: 'Japanese'},
    {value: 'language,Chinese', label: 'Chinese'},
    {value: 'language,Korean', label: 'Korean'},
    {value: 'language,Italian', label: 'Italian'},
    {value: 'language,German', label: 'German'},
    {value: 'language,Portuguese', label: 'Portuguese'},
    {value: 'language,Russian', label: 'Russian'},
    {value: 'language,Arabic', label: 'Arabic'},
    {value: 'language,Norwegian', label: 'Norwegian'},
    {value: 'language,Arabic', label: 'Arabic'},
    {value: 'language,Hindi', label: 'Hindi'},
    {value: 'language,Turkish', label: 'Turkish'},
    {value: 'language,Persian', label: 'Persian'},
    {value: 'language,Lithuanian', label: 'Lithuanian'},
    {value: 'language,Swedish', label: 'Swedish'},
    {value: 'language,Dutch', label: 'Dutch'},
    {value: 'language,Polish', label: 'Polish'},
    {value: 'language,Greek', label: 'Greek'},
    {value: 'language,Thai', label: 'Thai'},
    {value: 'language,Hebrew', label: 'Hebrew'},
    {value: 'language,Indonesian', label: 'Indonesian'},
    {value: 'language,Malay', label: 'Malay'},
    {value: 'language,Filipino', label: 'Filipino'},
    {value: 'language,Malayalam', label: 'Malayalam'},
    {value: 'language,Tamil', label: 'Tamil'},];

const sortOptions = [
    {value: 'movieId,asc', label: 'Most Popular'},
    {value: 'yearOfRelease,desc', label: 'Newest'},
    {value: 'yearOfRelease,asc', label: 'Oldest'},
    {value: 'movieTitle,asc', label: 'A → Z'},
    {value: 'movieTitle,desc', label: 'Z → A'}
];
const studioOptions = [
    {value: 'Walt Disney', label: 'Walt Disney'},
    {value: 'Pixar', label: 'Pixar'},
    {value: 'Warner Bros', label: 'Warner Bros'},
    {value: 'Marvel Studio', label: 'Marvel Studio'},
    {value: 'Studio Ghibli', label: 'Studio Ghibli'},
    {value: 'Paramount', label: 'Paramount'},
    {value: 'Universal Picture', label: 'Universal Picture'},
    {value: '20th Century Fox', label: '20th Century Fox'}
];

function checkParam(type, value) {
    if (type.includes(',')){
        const typeParts = type.split(',');
        return checkParam(typeParts[0], value);
    }
    switch (type) {
        case 'type':
            return genreOptionsNavbar.some(option => option.value === value)
                || studioOptions.some(option => option.value === value.replaceAll('%20', ' '));
        case 'genre':
            return genreOptions.some(option => option.value === value);
        case 'year':
            return yearOptions.some(option => option.value === value);
        case 'language':
            return languagesOptions.some(option => option.value === value);
        case 'sort':
            return sortOptions.some(option => option.value === value);
        case 'studio':
            return studioOptions.some(option => option.value === value);
        default:
            return false;
    }
}


module.exports = {
    genreOptions,
    genreOptionsNavbar,
    yearOptions,
    languagesOptions,
    sortOptions,
    studioOptions,
    springBootServerUrl,
    mongoDBServerUrl,
    checkParam
};