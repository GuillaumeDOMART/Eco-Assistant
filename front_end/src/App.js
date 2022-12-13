import logo from './logo.svg';
import './App.css';
import {Form} from "react-bootstrap";
import { useForm } from "react-hook-form";
import {getTest} from "./api_test";

function App() {
  const { register, handleSubmit, watch, formState: { errors } } = useForm();
  const onSubmit = data => console.log(data);
  getTest().then(value => console.log(value))
  console.log(watch("example")); // watch input value by passing the name of it
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          suiiiiiiiiiiiiiiiiii
        </a>

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

export default App;
