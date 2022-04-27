import React from 'react';

const Note = () => {
    return (
        <div>
            <div className="titre">Partager votre expérience</div>
            <form action="" className='formNote'>
                <div className="partOne">
                    <input type="datetime-local" />
                    <input type="number" max="5" min="1" step="1" placeholder="Note /5" />
                    <input type="number" min="1" step="1" placeholder="Temps d'attente" />
                </div>
                <textarea placeholder='Commentaire' className='comment'/>
                <input type="submit" value="Envoyer" id="send" className='buttonSend'/>
            </form>
        </div>
    );
};

export default Note;