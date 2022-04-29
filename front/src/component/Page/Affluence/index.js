import React, { useEffect, useState } from 'react';
import Chart from 'react-google-charts';

const Affluence = ({ dataWaitTime }) => {
    const [HistogramData, setHistogramData] = useState([
        ['', 'Temps attente'],
        ["11h15", 0],
        ["11h30", 0],
        ["11h45", 0],
        ["12h", 0],
        ["12h15", 0],
        ["12h30", 0],
        ["12h45", 0],
        ["13h", 0],
        ["13h15", 0],
        ["13h30", 0],
        ["13h45", 0],
        ["14h", 0],
        ["14h15", 0],
        ["14h30", 0],
        ["14h45", 0],
        ["15h", 0]]);

    const updateHist = (e) => {
        var day = e.target.value;
        setHistogramData([
            ['', 'Temps attente moyen (en min)'],
            ["11h15", dataWaitTime[day][1100]],
            ["11h30", dataWaitTime[day][1125]],
            ["11h45", dataWaitTime[day][1150]],
            ["12h", dataWaitTime[day][1175]],
            ["12h15", dataWaitTime[day][1200]],
            ["12h30", dataWaitTime[day][1225]],
            ["12h45", dataWaitTime[day][1250]],
            ["13h", dataWaitTime[day][1275]],
            ["13h15", dataWaitTime[day][1300]],
            ["13h30", dataWaitTime[day][1325]],
            ["13h45", dataWaitTime[day][1350]],
            ["14h", dataWaitTime[day][1375]],
            ["14h15", dataWaitTime[day][1400]],
            ["14h30", dataWaitTime[day][1425]],
            ["14h45", dataWaitTime[day][1450]],
            ["15h", dataWaitTime[day][1475]],
        ]);
    };

    const chartOptions = {
        legend: { position: "none" },
        colors: ['#5c6bf2'],
        backgroundColor: 'transparent',
        hAxis: {
            minValue: 0,
            textStyle:{color: 'white'}
        },
        vAxis: {
            gridlines: {
                color: "#5c6bf2"
            },
            baselineColor: '#5c6bf2',
            textStyle:{color: 'white'}
        },
    }

    return (
        <div className='histo'>
            <select className='selectDay' onChange={e => updateHist(e)}>
                <option>Choisir un jour</option>
                <option value="1">Lundi</option>
                <option value="2">Mardi</option>
                <option value="3">Mercredi</option>
                <option value="4">Jeudi</option>
                <option value="5">Vendredi</option>
                <option value="6">Samedi</option>
                <option value="0">Dimanche</option>
            </select>
            <div className="histChart">
                <Chart
                    width={'680px'}
                    height={'250px'}
                    chartType="ColumnChart"
                    loader={<div>Loading Chart</div>}
                    data={HistogramData}
                    options={chartOptions}
                />
            </div>
        </div>
    );
};

export default Affluence;