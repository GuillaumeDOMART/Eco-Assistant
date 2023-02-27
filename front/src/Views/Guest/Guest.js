import {useEffect} from "react";
import {useNavigate} from "react-router-dom";


/**
 * Page to create guest
 * @constructor the constructor
 */
function Guest(){
    const navigate = useNavigate();

    /**
     * Function to get token
     * @returns {Promise<void>} the promise
     */
    const getToken = async () => {
        const response = await fetch("api/auth/guest");
        const json = await response.json();
        const token = json.token
        sessionStorage.setItem("token", token)
        sessionStorage.setItem("guest",true)
    }

    useEffect( () => {
        getToken()
            .then(__ => navigate("/newproject"));
    }, [navigate]);
}

export default Guest