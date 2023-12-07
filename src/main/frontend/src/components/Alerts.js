import {useLocation} from "react-router-dom";
import React from "react";

export const Alerts = () => {
    const location = useLocation()
    let alertMessage = '';
    let alertType = ''
    if('state' in location && location.state !== null) {
        if('alertMessage' in location.state) {
            if('alertType' in location.state) {
                alertType = location.state.alertType
            } else {
                alertType = 'alert-primary'
            }
            alertMessage = location.state.alertMessage
        }
    }

    return (
        <div className="alert-container">
            {(alertMessage !== '') ? (
                <div className={"alert alert-dismissible " + alertType}>
                    <button type="button" className="btn-close" data-bs-dismiss="alert"></button>
                    {alertMessage}
                </div>
            ) : null}
        </div>
    )
}