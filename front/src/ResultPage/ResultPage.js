import Chart from "chart.js/auto";
import {useEffect, useRef} from "react";
import jsPDF from "jspdf";

function ResultPage() {
    const chartContainer = useRef(null);
    const chartInstance = useRef(null);
    const pdfContainer = useRef(null);
    const marginLeft = 15;
    const yText = 25;
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
                        console.log(jsonData.mine[array])
                        const results = jsonData.mine[array].map(item => item.result);
                        sums[array] = results.reduce((acc, current) => acc + current, 0);
                    } else {
                        sums[array] = 0;
                    }
                });
                chart(sums);
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
            console.log(jsonData.mine[array])
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