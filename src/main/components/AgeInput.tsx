import React, {Component} from "react";

interface AgeProps {
    player_age: number;
    setAge(player_age: number): void;
}

class AgeInput extends Component<AgeProps> {
    onAgeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.props.setAge(parseInt(event.target.value));
    };

    render() {
        return (
            <input
                value={this.props.player_age}
                onChange={this.onAgeChange}
            />
        )
    }
}

export default AgeInput;