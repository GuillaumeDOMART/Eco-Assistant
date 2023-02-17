import {useEffect} from "react";
import {useNavigate} from "react-router-dom";


function Guest(){
    const navigate = useNavigate();

    const getToken = async () => {
        const response = await fetch("api/auth/guest");
        const json = await response.json();
        const token = json.token
        sessionStorage.setItem("token", token)
    }

    useEffect( () => {
        getToken()
            .then(__ => navigate("/newproject"));
    }, [navigate]);
}

export default Guest