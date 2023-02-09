import Chart from "chart.js/auto";
import {useEffect, useRef, useState} from "react";
import { Bar } from "react-chartjs-2"
function ResultPage() {
    const [chartData, setChartData] = useState({});
    const chartContainer = useRef(null);
    const chartInstance = useRef(null);

    const chart = () => {
        if (chartInstance.current) {
            chartInstance.current.destroy();
        }

        const myChart = new Chart(chartContainer.current, {
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

        chartInstance.current = myChart;
    };

    useEffect(() => {
        chart();
    }, []);

    return (
        <div>
            <canvas ref={chartContainer} />
        </div>
    );
}

export default ResultPage;