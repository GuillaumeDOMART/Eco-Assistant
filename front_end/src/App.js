import logo from './logo.svg';
import './App.css';
import {Form} from "react-bootstrap";
import { useForm } from "react-hook-form";
import Chart from "chart.js/auto";
import { Bar } from "react-chartjs-2";


const labels = ["Planification", "Développement", "Test", "Déploiement", "Maintenance"];

const data = {
  labels: labels,
  datasets: [
    {
      label: labels[0],
      backgroundColor: "rgb(255, 99, 132)",
      borderColor: "rgb(255, 99, 132)",
      data: [0, 10, 5, 2, 20, 30, 45],
    },
  ],
};
function App() {
  const { register, handleSubmit, watch, formState: { errors } } = useForm();
  const onSubmit = data => console.log(data);
  const myChart = new Chart()

  console.log(watch("example")); // watch input value by passing the name of it
  return (
    <div className="App">
      <header className="App-header">
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          suiiiiiiiiiiii
        </a>
        <div id="myChart">
          <Bar data={data} />
        </div>
        <div id="myChartv2">

        </div>
        {/*---------React-BootStrap---------*/}
        <>
          <Form.Label>Range</Form.Label>
          <Form.Range />
        </>
        {/*  */}

        {/*---------React Hook Form---------*/}
        <form onSubmit={handleSubmit(onSubmit)}>
          {/* register your input into the hook by invoking the "register" function */}
          <input defaultValue="test" {...register("example")} />

          {/* include validation with required or other standard HTML validation rules */}
          <input {...register("exampleRequired", { required: true })} />
          {/* errors will return when field validation fails  */}
          {errors.exampleRequired && <span>This field is required</span>}

          <input type="submit" />
        </form>
        {/*  */}

      </header>
    </div>
  );
}
function generateChart() {
  const context = document.getElementById("myChartv2").getContext('2d');
  const myChart = new Chart(context, {
    type: 'bar',
    data: data,
    options: {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true
          }
        }]
      },
      onHover(e) {
        const activePoints = myChart.getElementsAtEventForMode(e, 'nearest', {
          intersect: true
        }, false)
        const [{
          index
        }] = activePoints;
        console.log(data.datasets[0].data[index]);
      }
    }
  });
  return myChart;
}
export default App;
