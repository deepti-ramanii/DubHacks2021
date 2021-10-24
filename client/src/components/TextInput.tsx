import React, {Component} from "react";

/*

Using this component:
https://www.thisdot.co/blog/composing-react-components-with-typescript
look at the "input" section
 */

interface TextInputState {
    inputValue: string;
}

interface TextInputProps {
    inputValue: string;
    onSubmit: (input: string) => void;
}

/**
 * Form allows user to input text and press a submit button.
 */
class TextInput extends Component<TextInputProps, TextInputState> {
    constructor(props: TextInputProps) {
        super(props);
        this.state = {
          inputValue: this.props.inputValue,
        };
    }

    // updates inputValue state
    onInputChange = (e:  React.ChangeEvent<HTMLInputElement>) => {
        this.setState({
            inputValue: e.target.value,
        });
    }

    // RENDERING METHODS ---------------------------------------------------------------------------
    render() {
        return (
            <div id="input-form">
                <p id="input-title">Form Name Here.</p>
                <input
                    id="input-box"
                    onChange={this.onInputChange}
                    placeholder={"Type input here..."}
                    value={this.state.inputValue}
                />
                <button
                    onClick={e => this.props.onSubmit(this.state.inputValue)}
                    id="input-submit"
                >
                    Submit
                </button>
            </div>
        );
    }
}

export default TextInput;
