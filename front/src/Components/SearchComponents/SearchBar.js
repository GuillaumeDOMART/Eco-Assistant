function SearchBar() {
    return(
    <form action="/" method="get">
        <label htmlFor="header-search">
            <span className="visually-hidden">Cherchez un utilisateur</span>
        </label>
        <input
            type="text"
            id="header-search"
            placeholder="Cherchez..."
            name="s"
        />
        <button type="submit">Search</button>
    </form>
    );
}

export default SearchBar;