import React, { useState } from 'react';

const Affluence = () => {
    const [day, setDay] = useState("Lundi");

    return (
        <div className='histo'>
            <select className='selectDay' onChange={e=>setDay(e.target.value)}>
                <option>Lundi</option>
                <option>Mardi</option>
                <option>Mercredi</option>
                <option>Jeudi</option>
                <option>Vendredi</option>
                <option>Samedi</option>
                <option>Dimanche</option>
            </select>

        </div>
    );
};

export default Affluence;