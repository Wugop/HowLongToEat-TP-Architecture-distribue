import React, { useEffect, useState } from 'react';
import List from './List';
import axios from "axios";

const Search = ({ setPage, listeResto, setListeResto }) => {
    const [inputSearch, setInputSearch] = useState("");

    const [error, setError] = useState(false);
    const [msg, setMsg] = useState("");

    const handleSearch = () => {
        axios({
            method: "GET",
            url: "http://localhost:3000/restaurant/api/v1/restaurantRessource",
            params: {
                city: inputSearch
            }
        }).then((res) => {
            setListeResto(res.data);
            if (res.data.length == 0) {
                setMsg("Aucun résultat trouvé.");
                setError(true);
            } else {
                setMsg("");
                setError(false);
            }

        }).catch(err => {
            console.log(err);
            setError(true);
            setMsg("Une erreur est survenue. Réessayez plus tard.")
        });
    };

    return (
        <div className="search-container">
            <div className="searchBar">
                <input type="text" id="searchBar" placeholder='Rechercher un restaurant ou un lieu' onChange={(e) => { setInputSearch(e.target.value) }} />
                <input type="image" id="icon" src='loupe.png' onClick={handleSearch} />
            </div>
            {listeResto.map(r => {
                let waitTime = 0;
                axios({
                    method: "GET",
                    url: "http://localhost:3000/note/api/v1/noteRessource/waitingTime",
                    params: {
                        idResto: r.idRestaurant
                    }
                }).then((res) => {
                    console.log(res.data);
                }).catch(err => {
                    console.log(err);
                });
                return <List dataResto={r} setPage={setPage} />
            })}
            {error && <div className="errorMessage">{msg}</div>}
        </div>
    );
};

export default Search;