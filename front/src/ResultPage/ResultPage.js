import Chart from "chart.js/auto";
import {useEffect, useState, useRef} from "react";
import jsPDF from "jspdf";

function ResultPage() {
    const chartContainer = useRef(null);
    const chartInstance = useRef(null);
    const pdfContainer = useRef(null);
    const [data, setData] = useState(null);
    const [sum, setSum] = useState(0);

    const chart = (result) => {
        if (chartInstance.current) {
            chartInstance.current.destroy();
        }

        chartInstance.current = new Chart(chartContainer.current, {
            type: 'bar',
            data: {
                labels: ["Planification", "Développement", "Test", "Déploiement", "Maintenance"],
                datasets: [
                    {
                        label: 'Consomation en CO2',
                        data: [12, result, 3, 5, 2, 3],
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderWidth: 1
                    }
                ]
            },
            options: {
                maintainAspectRatio: false
            }
        });
    };

    const handleDownloadPDF = () => {
        const canvas = chartContainer.current;
        const imgData = canvas.toDataURL('image/jpeg', 1.0);
        const pdf = new jsPDF("p","mm","a4");
        pdf.addImage(imgData, 'JPEG', 0, 0, 211, 298);
        pdf.save('chart.pdf');
        //TODO

    };

    useEffect(() => {
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ id: 1 })
        };
        fetch('api/calculs',options)
            .then(response => response.json())
            .then(jsonData => {
                setData(jsonData);
                const developpementResults = jsonData.developpement.map(item => item.result);
                const total = developpementResults.reduce((acc, current) => acc + current, 0);
                setSum(total);
                chart(total);
            });
    }, []);

    return (
        <div ref={pdfContainer}>
            <h1>Rapport de consomation de CO2</h1>
            <div>
                <canvas ref={chartContainer}/>

            </div>
            <button onClick={handleDownloadPDF}>Download PDF</button>
        </div>
    );
}

export default ResultPage;