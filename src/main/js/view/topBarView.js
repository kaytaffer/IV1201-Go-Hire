import React from "react";

export function TopBarView(props) {
    return (
        <div id={"top-bar"}>
            <div className={"left"}>
                <h1>Go Hire</h1>
            </div>
            <div className={"right"}>
                {props.user && <p>Logged in as: {props.user.username}</p>}
            </div>
        </div>
    )
}