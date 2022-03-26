import React from 'react';

const List = () => {
    return (
        <div className="list-container">
            <div className="infos">
                <div className="nomResto">Nom du Restaurant</div>
                <div className="adresseResto">20 rue des Capucins, 59300 Valenciennes</div>
                <div className="adresseResto">Ouvert - 11h à 14h</div>
            </div>
            <div className="temps">
                <div className="desc">Temps d'attente :</div>
                <div className="min">30'</div>
            </div>
        </div>
    );
};

export default List;