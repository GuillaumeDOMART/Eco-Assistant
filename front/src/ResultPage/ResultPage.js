import Chart from "chart.js/auto";
import {useEffect, useRef} from "react";
import jsPDF from "jspdf";

function ResultPage() {
    const chartContainer = useRef(null);
    const chartInstance = useRef(null);
    const pdfContainer = useRef(null);
    const lengthChart = 210;
    const heightChart = 297;
    const chart = () => {
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
                        data: [12, 19, 3, 5, 2, 3],
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
        const imgData = canvas.toDataURL('image/jpeg', 1.0, );
        const pdf = new jsPDF("p","mm","a4");
        pdf.text('Hello World!', 15, 25, { fontSize: 36, fontName: 'Helvetica', fontStyle: 'bold', color: '#000000', maxWidth: 170 });
        pdf.addImage(imgData, 'JPEG', 15, 35, lengthChart*0.9, heightChart/4);
        pdf.save('chart.pdf');
        //TODO

    };

    useEffect(() => {
        chart();
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