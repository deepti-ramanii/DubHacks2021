import React, {Component} from "react";

interface HobbyPrefProps {
    toggleUseHobbies(): void;
}

class HobbyPrefInput extends Component<HobbyPrefProps> {
    onUseHobbiesChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.props.toggleUseHobbies();
    };

    render() {
        return (
            <div className="imagerow">
                <span>Use hobbies when matching: </span>
                <input
                    type="checkbox"
                    onChange={this.onUseHobbiesChange}
                    defaultChecked={false}
                />
            </div>
        )
    }
}

export default HobbyPrefInput;