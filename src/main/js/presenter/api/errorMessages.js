/*
Pre-defined error objects with their error type matched with the message to be displayed to user.
 */

export const APPLICATION_ALREADY_HANDLED = {errorType: "APPLICATION_ALREADY_HANDLED", message: "This application has already been handled."}
export const INSUFFICIENT_CREDENTIALS = {errorType: "INSUFFICIENT_CREDENTIALS", message: "You do not have access rights to this."}
export const LOGIN_FAIL = {errorType: "LOGIN_FAIL", message: "Username and password do not match."}
export const SERVER_INTERNAL = {errorType: "SERVER_INTERNAL", message: "Something went wrong, please try again later."}
export const USER_INPUT_ERROR = {errorType: "USER_INPUT_ERROR", message: "Please follow the form's required input examples."}
export const USERNAME_ALREADY_EXISTS = {errorType: "USERNAME_ALREADY_EXISTS", message: "The suggested username already exists."}
export const PAGE_DOES_NOT_EXIST = {errorType: "PAGE_DOES_NOT_EXIST", message: "The requested page or resource does not exist or is unavailable."}
