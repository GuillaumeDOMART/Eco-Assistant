import logo from './logo.svg';
import './App.css';
import {Form} from "react-bootstrap";
import { useForm } from "react-hook-form";
import Chart from "chart.js/auto";
import { Bar } from "react-ù-2";
import { jsPDF } from "jspdf";


const labels = ["Planification", "Développement", "Test", "Déploiement", "Maintenance"];
// Default export is a4 paper, portrait, using millimeters for units



const data = {
  labels: labels,
  datasets: [
    {
      label: labels[0],
      backgroundColor: "rgb(255, 99, 132)",
      borderColor: "rgb(255, 99, 132)",
      data: [0, 10, 5, 2, 20, 30, 45],
      barPercentage:1.0
    },
  ],
};
function App() {
  const { register, handleSubmit, watch, formState: { errors } } = useForm();
  const onSubmit = data => console.log(data);
//add event listener to button

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
        <div style={{width: 500, height: 500}}>
          <Bar data={data} id="myChart" />
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
        <button type="button" id="download-pdf" onClick={downloadPDF}> Export PDF </button>
        {/*  */}

      </header>
    </div>
  );
}


function downloadPDF() {
  var canvas = document.querySelector('#myChart');
  var context = canvas.getContext('2d');

  var canvasImg = canvas.toDataURL("image/jpeg", 1.0);
  var doc = new jsPDF();
  var width = doc.internal.pageSize.getWidth();
  var height = doc.internal.pageSize.getHeight();
  doc.setFontSize(20);

  doc.addImage(canvasImg, 'JPEG', 10, 10, width*0.8, height*0.4 );
  doc.save('canvas.pdf');
}
export default App;
