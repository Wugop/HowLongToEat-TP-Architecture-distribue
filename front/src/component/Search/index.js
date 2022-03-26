import React from 'react';
import List from './List';

const Search = () => {
    return (
        <div className="search-container">
            <div className="searchBar">
                <input type="text" id="searchBar" placeholder='Rechercher un restaurant ou un lieu' />
                <img src='loupe.png' id="icon"></img>
            </div>
            <List />
            <List />
            <List />
            <List />
            <List />
            <List />
        </div>
    );
};

export default Search;