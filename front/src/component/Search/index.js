import React, { useEffect, useState } from 'react';
import List from './List';
import axios from "axios";

const Search = () => {
    const [inputSearch, setInputSearch] = useState("");
    const [listeResto, setListeResto] = useState([]);

    const handleSearch = () => {
        const dataResto = {
            nomResto: "",
            adresseResto: "",
            horaires: "",
            tmps: 0
        };

        axios({
            method: "GET",
            url: "http://localhost:3001/restos",
            params: {
                search: inputSearch
            }
        }).then((res) => {
            setListeResto(res.data);
        }).catch(err => console.log(err));
    };

    return (
        <div className="search-container">
            <div className="searchBar">
                <input type="text" id="searchBar" placeholder='Rechercher un restaurant ou un lieu' onChange={(e) => { setInputSearch(e.target.value) }} />
                <input type="image" id="icon" src='loupe.png' onClick={handleSearch} />
            </div>
            {listeResto.map(r => {
                    return <List dataResto={r}/>
                })}
        </div>
    );
};

export default Search;