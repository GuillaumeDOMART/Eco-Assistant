import Chart from "chart.js/auto";
import {useCallback, useEffect, useRef, useState} from "react";
import jsPDF from "jspdf";
import {Button} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import "./ResultPage.css"

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
    const [consommationTotal, setConsommationTotal] = useState(0);

    //const [paragrapheP, setParagrapheP] = useState("");
    //const [paragrapheD, setParagrapheD] = useState("");
    //const [paragrapheT, setParagrapheT] = useState("");
    //const [paragrapheDE, setParagrapheDE] = useState("");
    //const [paragrapheM, setParagrapheM] = useState("");

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

    /**
     * Function to create the chart
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
                legend: {
                    position: 'top',
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            callback(value, __, ___) {
                                return  `${value}kg CO2e`;
                            }
                        },
                        title: {
                            display: true,
                            text: 'Consomation de la phase en kg CO2e'
                        }
                    },
                    x:{
                        beginAtZero: true,
                        ticks: {
                            callback(value, __, ___) {
                                return `${value}j`;
                            }
                        },
                        title: {
                            display: true,
                            text: 'Durée de la phase en jour'
                        }
                    }
                }
            }
        });
        window.addEventListener('resize', () => chartInstance.current.resize());

    };

    /**
     * Function to create the pdf
     */
    const handleDownloadPDF =  useCallback(() => {
        const canvas = chartContainerAll.current;
        const imgData = canvas.toDataURL('image/png', 1.0);
        const pdf = new jsPDF("p", "mm", "a4");
        const diviseur = Math.ceil(pdf.getImageProperties(imgData).width / (A4.w - 20))
        pdf.text('Rapport de consommation de CO2', marginLeft, yText, {
            fontSize: 36,
            fontName: 'Helvetica',
            fontStyle: 'bold',
            color: '#000000',
            maxWidth: 170
        });
        pdf.addImage(imgData, 'JPEG', 15, 40, pdf.getImageProperties(imgData).width / diviseur, pdf.getImageProperties(imgData).height / diviseur);
        pdf.text('Pour une consommation total de '+consommationTotal, marginLeft, 66, {
            fontSize: 18,
            fontName: 'Helvetica',
            fontStyle: 'bold',
            color: '#000000',
            maxWidth: 170
        });

        const canvasP = chartContainerPlanification.current;
        const imgDataP = canvasP.toDataURL('image/png', 1.0);
        pdf.text('Rapport de consommation de CO2 pour la phase de planification', 15, 76, {
            fontSize: 36,
            fontName: 'Helvetica',
            fontStyle: 'bold',
            color: '#000000',
            maxWidth: 170
        });
        pdf.addImage(imgDataP, 'JPEG',15,80, pdf.getImageProperties(imgData).width / diviseur, (pdf.getImageProperties(imgData).height / diviseur)*2.5);

        const canvasD = chartContainerDeveloppement.current;
        const imgDataD = canvasD.toDataURL('image/png', 1.0);
        pdf.text('Rapport de consommation de CO2 pour la phase de dévelopement', 15, 131, {
            fontSize: 36,
            fontName: 'Helvetica',
            fontStyle: 'bold',
            color: '#000000',
            maxWidth: 170
        });
        pdf.addImage(imgDataD, 'JPEG',15, 135,  pdf.getImageProperties(imgData).width / diviseur, (pdf.getImageProperties(imgData).height / diviseur)*2.5);

        const canvasT = chartContainerTest.current;
        const imgDataT = canvasT.toDataURL('image/png', 1.0);
        pdf.text('Rapport de consommation de CO2 pour la phase de test', 15, 186, {
            fontSize: 36,
            fontName: 'Helvetica',
            fontStyle: 'bold',
            color: '#000000',
            maxWidth: 170
        });
        pdf.addImage(imgDataT, 'JPEG', 15, 190, pdf.getImageProperties(imgData).width / diviseur, (pdf.getImageProperties(imgData).height / diviseur)*2.5);

        const canvasDE = chartContainerDeploiment.current;
        const imgDataDE = canvasDE.toDataURL('image/png', 1.0);
        pdf.text('Rapport de consommation de CO2 pour la phase de déploiment', 15, 241, {
            fontSize: 36,
            fontName: 'Helvetica',
            fontStyle: 'bold',
            color: '#000000',
            maxWidth: 170
        });
        pdf.addImage(imgDataDE, 'JPEG', 15, 245,pdf.getImageProperties(imgData).width / diviseur, (pdf.getImageProperties(imgData).height / diviseur)*2.5);

        pdf.addPage()

        const canvasM = chartContainerMaintenance.current;
        const imgDataM = canvasM.toDataURL('image/png', 1.0);
        pdf.text('Rapport de consommation de CO2 pour la phase de maintenance', 15, 25, {
            fontSize: 36,
            fontName: 'Helvetica',
            fontStyle: 'bold',
            color: '#000000',
            maxWidth: 170
        });
        pdf.addImage(imgDataM, 'JPEG', 15,29, pdf.getImageProperties(imgData).width / diviseur, (pdf.getImageProperties(imgData).height / diviseur)*2.5);


        pdf.save('chart.pdf');
    }, [consommationTotal,A4.w])

    /**
     * the function to quit
     */
    const handleQuit = useCallback(() =>  {
        if(sessionStorage.getItem("guest")){
            navigate("/logout")
        }
        else {
            navigate("/profil")
        }
    },[navigate])

    useEffect(() => {
        const id = new URLSearchParams(window.location.search).get('id');
        const token = sessionStorage.getItem("token")
        //const para = {
        //    planification: setParagrapheP,
        //    developpement: setParagrapheD,
        //    test: setParagrapheT,
        //    deploiement: setParagrapheDE,
        //    maintenance: setParagrapheM
        //}
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({id})
        };
        fetch('api/calculs',options)
            .then(response => {
                if(response.status === 403) {
                    navigate("/")
                }
                return response.json();
            })
            .catch((_) => {
                navigate("/profil")
            })
            .then(jsonData => {
                const arrays = ['planification', 'developpement', 'test', 'deploiement', 'maintenance'];
                const sums = {};
                arrays.forEach(array => {
                    if (jsonData.mine[array]) {
                        const results = jsonData.mine[array].map(item => item.result);
                        sums[array] = results.reduce((acc, current) => acc + current, 0);
                        //const intitule = jsonData.mine[array].map(item => item.intitule).join("<br>").replace(/\n/g, "<br>")
                        //para[array](intitule)
                    } else {
                        sums[array] = 0;
                    }
                });
                chart(sums, chartContainerAll, chartInstanceAll);
                const sumAll = Object.values(sums).reduce((acc, val) => acc + val, 0);
                setConsommationTotal(sumAll)
                arrays.forEach(array => {
                    const values = []
                    const mineTime = jsonData.mine[`duration${array.charAt(0).toUpperCase()}${array.slice(1)}`]
                    const mineTimeValue = mineTime === null ? 0 : mineTime;
                   jsonData.others.forEach(other => {
                       const results = other[array].map(item => item.result);
                       const sum = results.reduce((acc, current) => acc + current, 0);
                       const time = other[`duration${array.charAt(0).toUpperCase()}${array.slice(1)}`]
                       const timeValue = time === null ? 0 : time;
                       values.push({y: sum,x: timeValue})
                   })
                    const data =  [
                        {
                            label: 'Votre projet',
                            data: [
                                { y: sums[array], x: mineTimeValue }
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
                    chartbulet(array, data)
                });
            });
    }, [navigate]);
    return (
        <div ref={pdfContainer}>
            <br/>
            <Button onClick={handleDownloadPDF} type="button">Download PDF</Button>
            <Button onClick={handleQuit} type="button">Retourner au menu</Button>
            <br/>

            <h1>Rapport de consommation de CO2</h1>
            <div>
                <canvas ref={chartContainerAll}/>
            </div>
            <h2>Pour une consommation total de {consommationTotal}</h2>

            <h1>Rapport de consommation de CO2 pour la phase de planification</h1>
            <div className="divcanvas">
                <canvas ref={chartContainerPlanification}/>
            </div>
            <h1>Rapport de consommation de CO2 pour la phase de dévelopement</h1>
            <div className="divcanvas">
                <canvas ref={chartContainerDeveloppement}/>
            </div>
            <h1>Rapport de consommation de CO2 pour la phase de test</h1>
            <div className="divcanvas">
                <canvas ref={chartContainerTest}/>

            </div>
            <h1>Rapport de consommation de CO2 pour la phase de deploiement </h1>
            <div className="divcanvas">
                <canvas ref={chartContainerDeploiment}/>

            </div>
            <h1>Rapport de consommation de CO2 pour la phase de maintenance</h1>
            <div className="divcanvas">
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