import React from 'react';
import { UserContext } from '../../App';
import Note from './Note';

const Page = ({ dataResto }) => {
    const context = React.useContext(UserContext);

    return (
        <div className='page'>
            <div className="infosDetails">
                <div className="infosRestos">
                    <div className="nom">{dataResto.name}</div>
                    <div className="desc">{dataResto.description}</div>
                    <div className="hourly">Horaires : {dataResto.hourly}</div>
                    <div className="phone">Téléphone : {dataResto.phone}</div>
                    <div className="city">Ville : {dataResto.city}</div>
                    <div className="adress">Adresse : {dataResto.adress}</div>
                </div>
                <img src="https://www.telerama.fr/sites/tr_master/files/styles/simplecrop1000/public/fauves13_0.jpg?itok=SAX57ak4" />
            </div>
            <div className="affluence">
                <div className="titre">Affluence</div>
            </div>
            <div className="inputHeures">
                {context.token && <Note/>}
                {!context.token && <div className="connect">Connectez-vous pour partger votre temps d'attente dans ce restaurant.</div>}
            </div>
        </div>

    );
};

export default Page;