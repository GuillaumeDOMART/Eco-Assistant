import { useForm } from "react-hook-form";
import React, {useEffect, useState} from "react";

// The following component is an example of your existing Input Component
const Input = ({ label, register, required }) => (
    <>
        <label>{label}</label>
        <input {...register(label, { required })} />
    </>
);

// you can use React.forwardRef to pass the ref too
const Select = React.forwardRef(({ onChange, onBlur, name, label }, ref) => (
    <>
        <label>{label}</label>
        <select name={name} ref={ref} onChange={onChange} onBlur={onBlur}>
            <option value="20">20</option>
            <option value="30">30</option>
        </select>
    </>
));

function Questionnaire() {
    const { register, handleSubmit } = useForm();
    const [questionnaire, onQuestionnaire] = useState([])

    const onSubmit = (data) => {
        alert(JSON.stringify(data));
    };

    useEffect(() => {/*{
        fetch('localhost:8001/api/test')
            .then((response) => response.text())
            .then(data => console.log(data))
    }*/

        const requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        fetch("/api/test", requestOptions)
            .then(response => response.text())
            .then(result => console.log(result))
            .catch(error => console.log('error', error))
    })


    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <Input label="First Name" register={register} required />
            <Select label="Age" {...register("Age")} />
            <input type="submit" />
        </form>
    );
}

export default Questionnaire