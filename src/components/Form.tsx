import React, {Component} from "react";

/*

Using this component:
https://www.thisdot.co/blog/composing-react-components-with-typescript
look at the "input" section
 */

interface FormState {
}

interface FormProps {
    inputValue: string;
    onSetInputValue: (input: string) => void;
    handleSubmit: () => void;
}

/**
 * Form allows user to input string.
 */
class Form extends Component<FormProps, FormState> {
    constructor(props: FormProps) {
        super(props);
    }

    // RENDERING METHODS ---------------------------------------------------------------------------
    render() {
        return (
            <div id="form">
                <span id="form-label">Form Name Here.</span>
                <input
                    onChange={event => this.props.onSetInputValue(event.target.value)}
                    className="form"
                    placeholder={"Type here..."}
                    value={this.props.inputValue}
                />
                <button onClick={this.props.handleSubmit} id="form-submit">
                    Submit
                </button>
            </div>
        );
    }



}

export default Form;
