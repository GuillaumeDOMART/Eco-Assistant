import {useEffect} from "react";
import {useNavigate} from "react-router-dom";


/**
 * Page for the logout
 * @constructor the constructor
 */
function Deconnection() {
    const navigate = useNavigate();

    useEffect(() => {
        const token = new URLSearchParams(window.location.search).get('token');

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Authorization", `Bearer ${token}`);

        const requestOptions = {
            method: 'PATCH',
            headers: myHeaders,
            redirect: 'follow'
        };
        fetch("/api/profil/register", requestOptions)
            .then(response => {
                if (response.status === 403) {

                } else {

                }
            })
    }, [navigate]);

}

export default Deconnection