import React, {Component} from "react";

interface AgePrefProps {
    min_age: number;
    max_age: number;
    setMinAge(min_age: number): void;
    setMaxAge(min_age: number): void;
}

class AgePrefInput extends Component<AgePrefProps> {
    onMinAgeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.props.setMinAge(parseInt(event.target.value));
    };
    onMaxAgeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.props.setMaxAge(parseInt(event.target.value));
    };

    render() {
        return (
            <div id="age_range">
                <span>Play with people between ages </span>
                <input
                    value={this.props.min_age}
                    onChange={this.onMinAgeChange}
                />
                <span> and </span>
                <input
                    value={this.props.max_age}
                    onChange={this.onMaxAgeChange}
                />
            </div>
        )
    }
}

export default AgePrefInput;