import React from "react";

/**
 * Responsible for rendering user notice messages.
 * @param props props.
 * @param {String} props.message - the message to display to the user.
 * @param {boolean} props.error - true if the message is an error, false if it is information.
 * @returns {JSX.Element} the rendered user notice message.
 */
export function UserNoticeView(props) {
    return <div id="user-notice">{props.message}</div>
}