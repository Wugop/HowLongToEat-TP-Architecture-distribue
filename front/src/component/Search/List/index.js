import React from 'react';

const List = ({dataResto, setPage}) => {

    const afficherPage = () => {
        setPage(dataResto);
    };

    return (
        <div className="list-container" onClick={afficherPage}>
            <div className="infos">
                <div className="nomResto">{dataResto.name}</div>
                <div className="adresseResto">{dataResto.adress}</div>
                <div className="adresseResto">Ouvert - {dataResto.hourly}</div>
            </div>
            <div className="temps">
                <div className="desc">Temps d'attente :</div>
                <div className="min">-</div>
            </div>
        </div>
    );
};

export default List;