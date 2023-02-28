import React, {useCallback, useState} from "react";
import "./AccueilSite.css";
import {TextField, Tooltip} from "@mui/material";

const StrengthMeter = ({register, fieldErrors}) => {

    const [password, setPassword] = useState("");

    const [progress, setProgress] = useState({strength: 0, val: "", pwdCheck: [true, true, true, true, true]});

    const pwdChecker = useCallback((event) => {
        const passwd = event.target.value;
        console.log(passwd)
        let pwdCheck = [false, false, false, false, false];
        let validateRegex = [".{8,}", "[A-Z]", "[a-z]", "[0-9]", "[\\W_]"];
        for (let i = 0; i < validateRegex.length; i++) {
            const regex = new RegExp(validateRegex[i]);
            if (regex.test(passwd)) {
                pwdCheck[i] = true;
            }
        }
        console.log(pwdCheck.filter(v => v === true))
        switch (pwdCheck.filter(v => v === true).length) {
            case 1:
                setProgress({strength: 1, val: "faible", pwdCheck: pwdCheck});
                break;
            case 2:
                setProgress({strength: 2, val: "passable", pwdCheck: pwdCheck});
                break;
            case 3:
                setProgress({strength: 3, val: "correct", pwdCheck: pwdCheck});
                break;
            case 4:
                setProgress({strength: 3, val: "bon", pwdCheck: pwdCheck});
                break;
            case 5:
                setProgress({strength: 4, val: "fort", pwdCheck: pwdCheck});
                break;
            default:
                setProgress({strength: 0, val: "", pwdCheck: pwdCheck});
        }
        setPassword(passwd);
    }, []);

    return (
        <>
            <Tooltip
                title={"Le mot de passe doit être composé de 8 caractères minimum dont au moins 1 minuscule, 1 MAJUSCULE, 1 chiffre et 1 caractère spécial"}
                arrow>
                <TextField label="Mot de passe" type="password" variant="standard"
                           className="textfield " {...register("password")} value={password} onChange={pwdChecker}
                           required
                           error={!!fieldErrors.password}
                           helperText={fieldErrors.password}/>
            </Tooltip><br/>

            <progress
                className={`pwd-checker-bar strength-${progress.val}`}
                value={progress.strength}
                max="4"
            />
        </>
    );
};
export default StrengthMeter;