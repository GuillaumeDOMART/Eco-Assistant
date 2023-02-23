import Chart from "chart.js/auto";
import {useCallback, useEffect, useRef} from "react";
import jsPDF from "jspdf";
import {Button} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

/**
 * Page of the result
 * @returns {JSX.Element} the jsx element
 * @constructor the constructor
 */
function ResultPage() {
    const chartContainerAll = useRef(null);
    const chartInstanceAll = useRef(null);

    const chartContainerPlanification = useRef(null);
    const chartInstancePlanification = useRef(null);
    const chartContainerDeveloppement = useRef(null);
    const chartInstanceDeveloppement = useRef(null);
    const chartContainerTest = useRef(null);
    const chartInstanceTest = useRef(null);
    const chartContainerDeploiment = useRef(null);
    const chartInstanceDeploiement = useRef(null);
    const chartContainerMaintenance = useRef(null);
    const chartInstanceMaintenance = useRef(null);

    const pdfContainer = useRef(null);
    const navigate = useNavigate();
    const marginLeft = 15;
    const yText = 25;
    const A4 = {
        "h": 297,
        "w": 210
    }

    /**
     * Function to create the chart
     * @param result the result
     * @param chartContainer chartContainer
     * @param chartInstance chartInstance
     */
    const chart = (result, chartContainer, chartInstance) => {
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
                        data: [result.planification, result.developpement, result.test, result.deploiement, result.maintenance],
                        backgroundColor: 'rgba(100, 198, 146, 0.2)',
                        borderWidth: 1
                    }
                ]
            },
            options: {
                maintainAspectRatio: false
            }
        });
    };

    /**
     * Function to create the chart
     * @param result the result
     * @param phase the phase
     * @param data the data
     */
    const chartbulet = (phase, data) => {
        const instance = {
            planification: [chartContainerPlanification, chartInstancePlanification],
            developpement: [chartContainerDeveloppement, chartInstanceDeveloppement],
            test: [chartContainerTest, chartInstanceTest],
            deploiement: [chartContainerDeploiment, chartInstanceDeploiement],
            maintenance: [chartContainerMaintenance, chartInstanceMaintenance]
        }

        const chartContainer = instance[phase][0]
        const chartInstance = instance[phase][1]
        if (chartInstance.current) {
            chartInstance.current.destroy();
        }

        chartInstance.current = new Chart(chartContainer.current, {
            type: 'scatter',
            data: {
                datasets: data
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    scales: {
                        y: {
                            beginAtZero: true
                        },
                        x:{
                            beginAtZero: true
                        }
                    }
                }
            }
        });
    };

    /**
     * Function to create the pdf
     */
    const handleDownloadPDF =  useCallback(() => {
        const canvas = chartContainerAll.current;
        const imgData = canvas.toDataURL('image/png', 1.0);
        const pdf = new jsPDF("p","mm","a4");
        const diviseur = Math.ceil(pdf.getImageProperties(imgData).width/(A4.w-20))
        pdf.text('Hello World!', marginLeft, yText, { fontSize: 36, fontName: 'Helvetica', fontStyle: 'bold', color: '#000000', maxWidth: 170 });
        pdf.addImage(imgData, 'JPEG', 15, 40, pdf.getImageProperties(imgData).width/diviseur, pdf.getImageProperties(imgData).height/diviseur);
        pdf.save('chart.pdf');
        //a finir

    },[A4.w])

    /**
     * the function to quit
     */
    const handleQuit = useCallback(() =>  {
        navigate("/profil")
    },[navigate])

    useEffect(() => {
        const id = new URLSearchParams(window.location.search).get('id');
        const token = sessionStorage.getItem("token")
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({id })
        };
        fetch('api/calculs',options)
            .then(response => response.json())
            .then(jsonData => {
                const arrays = ['planification', 'developpement', 'test', 'deploiement', 'maintenance'];
                const sums = {};
                arrays.forEach(array => {
                    if (jsonData.mine[array]) {
                        const results = jsonData.mine[array].map(item => item.result);
                        sums[array] = results.reduce((acc, current) => acc + current, 0);
                    } else {
                        sums[array] = 0;
                    }
                });
                chart(sums, chartContainerAll, chartInstanceAll);

                arrays.forEach(array => {
                    const values = []
                    const mineTime = jsonData.mine["duration"+ array.charAt(0).toUpperCase() + array.slice(1)]
                    const mineTimeValue = mineTime == null ? 0 : mineTime;
                   jsonData.others.forEach(other => {
                       const results = other[array].map(item => item.result);
                       const sum = results.reduce((acc, current) => acc + current, 0);
                       const time = other["duration"+ array.charAt(0).toUpperCase() + array.slice(1)]
                       const timeValue = time == null ? 0 : time;
                       values.push({x: sum,y: timeValue})
                   })
                    const data =  [
                        {
                            label: 'Votre projet',
                            data: [
                                { x: sums[array], y: mineTimeValue }
                            ],
                            backgroundColor: 'rgba(255, 99, 132, 0.2)',
                            borderColor: 'rgba(255, 99, 132, 1)',
                            borderWidth: 1
                        },
                        {
                            label: 'Autre projet',
                            data: values,
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        }
                    ];
                    console.log(data)
                    chartbulet(array, data)
                });
            });
    }, []);



    return (
        <div ref={pdfContainer}>
            <h1>Rapport de consomation de CO2</h1>
            <div>
                <canvas ref={chartContainerAll}/>

            </div>
            <Button onClick={handleDownloadPDF} type="button">Download PDF</Button>
            <Button onClick={handleQuit} type="button">Retourner au menu</Button>

            <h1>Rapport de consomation de CO2 pour la planification</h1>
            <div>
                <canvas ref={chartContainerPlanification}/>

            </div>
            <h1>Rapport de consomation de CO2 pour le dévlopement</h1>
            <div>
                <canvas ref={chartContainerDeveloppement}/>

            </div>
            <h1>Rapport de consomation de CO2 pour le test</h1>
            <div>
                <canvas ref={chartContainerTest}/>

            </div>
            <h1>Rapport de consomation de CO2 pour le deploiment </h1>
            <div>
                <canvas ref={chartContainerDeploiment}/>

            </div>
            <h1>Rapport de consomation de CO2 pour la maintenace</h1>
            <div>
                <canvas ref={chartContainerMaintenance}/>

            </div>
        </div>
    );
}

export default ResultPage;

/*
function ResultPage() {
    const chartContainer = useRef(null);
    const chartInstance = useRef(null);
    const pdfContainer = useRef(null);
    const lengthChart = 150;
    const heightChart = 300;
    const marginLeft = 15;
    const yText = 25;
    const yImage = 35;

    const A4 = {
        "h": 297,
        "w": 210
    }

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
                        label: 'Consommation en CO2',
                        data: [result.planification, result.developpement, result.test, result.deploiement, result.maintenance],
                        backgroundColor: 'rgba(100, 198, 146, 0.2)',
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
        const imgData = canvas.toDataURL('image/png', 1.0);


        const pdf = new jsPDF("p","mm","a4");
        const diviseur = Math.ceil(pdf.getImageProperties(imgData).width/(A4.w-20))
        pdf.text('Hello World!', marginLeft, yText, { fontSize: 36, fontName: 'Helvetica', fontStyle: 'bold', color: '#000000', maxWidth: 170 });
        pdf.addImage(imgData, 'JPEG', 15, 40, pdf.getImageProperties(imgData).width/diviseur, pdf.getImageProperties(imgData).height/diviseur);

        pdf.save('chart.pdf');
        //TODO

    };

    useEffect(() => {
    const id = new URLSearchParams(window.location.search).get('id');

    const jsonData = {
        "mine": {
            "planification": [],
            "developpement": [
                {
                    "result": 33.0,
                    "intitule": "test2"
                },
                {
                    "result": 0.0,
                    "intitule": "test3"
                }
            ],
            "test": [],
            "deploiement": [],
            "maintenance": [],
            "horsPhase": [],
            "durationPlanification": null,
            "durationDeveloppement": 0.0,
            "durationTest": null,
            "durationDeploiement": null,
            "durationMaintenance": null
        },
        "others": [
            {
                "planification": [],
                "developpement": [
                    {
                        "result": 33.0,
                        "intitule": "test2"
                    },
                    {
                        "result": 0.0,
                        "intitule": "test3"
                    }
                ],
                "test": [],
                "deploiement": [],
                "maintenance": [],
                "horsPhase": [],
                "durationPlanification": null,
                "durationDeveloppement": 0.0,
                "durationTest": null,
                "durationDeploiement": null,
                "durationMaintenance": null
            },
            {
                "planification": [],
                "developpement": [],
                "test": [],
                "deploiement": [],
                "maintenance": [],
                "horsPhase": [],
                "durationPlanification": null,
                "durationDeveloppement": null,
                "durationTest": null,
                "durationDeploiement": null,
                "durationMaintenance": null
            },
            {
                "planification": [],
                "developpement": [],
                "test": [],
                "deploiement": [],
                "maintenance": [],
                "horsPhase": [],
                "durationPlanification": null,
                "durationDeveloppement": null,
                "durationTest": null,
                "durationDeploiement": null,
                "durationMaintenance": null
            }
        ]
    }

    const arrays = ['planification', 'developpement', 'test', 'deploiement', 'maintenance'];
    const sums = {};
    arrays.forEach(array => {
        if (jsonData.mine[array]) {
            const results = jsonData.mine[array].map(item => item.result);
            sums[array] = results.reduce((acc, current) => acc + current, 0);
        } else {
            sums[array] = 0;
        }
    });
    chart(sums);
});


return (
    <div className="p-3" ref={pdfContainer}>
        <h1>Rapport de consommation de CO2</h1>
        <div className="p-3">
            <canvas  ref={chartContainer} height={heightChart}/>

        </div>
        <button onClick={handleDownloadPDF}>Download PDF</button>
    </div>
);
}

export default ResultPage;*/