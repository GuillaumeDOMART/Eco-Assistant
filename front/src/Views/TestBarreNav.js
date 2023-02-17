import BarreNavCore from "../Components/BarreNav/BarreNavCore";
import React from "react";
import {Button} from "react-bootstrap";

export function TestBarreNav(){
    return(
        <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <div className="col-10 p-5">
                <Button variant="primary">Primary</Button>{' '}
                <Button variant="secondary">Secondary</Button>{' '}
                <Button variant="success">Success</Button>{' '}
                <Button variant="warning">Warning</Button>{' '}
                <Button variant="danger">Danger</Button>{' '}
                <Button variant="info">Info</Button>{' '}
                <Button variant="light">Light</Button>{' '}
                <Button variant="dark">Dark</Button>
                <Button variant="link">Link</Button>
            </div>
        </div>
    );
}