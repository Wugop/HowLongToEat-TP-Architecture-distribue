import React from 'react';

const List = ({dataResto}) => {
    return (
        <div className="list-container">
            <div className="infos">
                <div className="nomResto">{dataResto.nomResto}</div>
                <div className="adresseResto">{dataResto.adresseResto}</div>
                <div className="adresseResto">Ouvert - {dataResto.horaires}</div>
            </div>
            <div className="temps">
                <div className="desc">Temps d'attente :</div>
                <div className="min">{dataResto.tmps}'</div>
            </div>
        </div>
    );
};

export default List;