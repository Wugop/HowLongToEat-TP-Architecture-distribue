import React, { useState } from 'react';
import { UserContext } from '../../../App';
import axios from 'axios';

const Note = ({dataResto, setPosted, setError}) => {
    const [date, setDate] = useState();
    const [note, setNote] = useState(1);
    const [tps, setTps] = useState();
    const [comm, setComm] = useState();

    const context = React.useContext(UserContext);
    
    const handleSubmit = (e) => {
        e.preventDefault();
        axios({
            method: "POST",
            url: "http://localhost:3001/addNote",
            data: {
                note: note,
                temps: tps,
                datePassage: date,
                comment: comm,
                idUserN: context.username,
            }
        }).then(() => {
            setPosted("Merci de partager votre expérience dans ce restaurant. Votre participation a été enregistrée.")
        }).catch(err => {
            console.log(err);
            setPosted("Une erreur est survenue, réessayez plus tard.")
        });
    };

    return (
        <div>
            <div className="titre">Partager votre expérience</div>
            <form action="" onSubmit={(e)=>handleSubmit(e)} className='formNote'>
                <div className="partOne">
                    <input type="datetime-local" onChange={e=>setDate(e.target.value)}/>
                    <select placeholder='Note' onChange={e=>setNote(e.target.value)}>
                        <option className='placeholderNote'>Note</option>
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                        <option>5</option>
                    </select>
                    <input type="number" min="1" step="1" placeholder="Temps d'attente (en min)" onChange={e=>setTps(e.target.value)}/>
                </div>
                <textarea placeholder='Commentaire' className='comment' onChange={e=>setComm(e.target.value)}/>
                <input type="submit" value="Envoyer" id="send" className='buttonSend'/>
            </form>
        </div>
    );
};

export default Note;