import React from "react";

/**
 * Responsible for rendering the container around the main page content
 * @param props - props
 * @param {JSX.Element} props.children - the main page content
 * @returns {JSX.Element} the rendered main page section
 */
export function MainContentView(props) {
    return <div id={"content-container"}>{props.children}</div>
}