import React from 'react';

const Note = () => {
    return (
        <div>
            <div className="titre">Partager votre expérience</div>
            <form action="" className='formNote'>
                <div className="partOne">
                    <input type="datetime-local" />
                    <select placeholder='Note'>
                        <option className='placeholderNote'>Note</option>
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                        <option>5</option>
                    </select>
                    <input type="number" min="1" step="1" placeholder="Temps d'attente (en min)" />
                </div>
                <textarea placeholder='Commentaire' className='comment'/>
                <input type="submit" value="Envoyer" id="send" className='buttonSend'/>
            </form>
        </div>
    );
};

export default Note;