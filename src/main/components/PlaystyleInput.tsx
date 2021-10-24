import React, {Component} from "react";

interface PlaystyleProps {
    toggleCompetitive(): void;
    toggleUsesVC(): void;
}

class PlaystyleInput extends Component<PlaystyleProps> {
    onCompetitiveChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.props.toggleCompetitive();
    };

    onUsesVCChange = (event: React.ChangeEvent<HTMLInputElement>)  => {
        this.props.toggleUsesVC();
    }

    render() {
        return (
            <div className="imagerow">
                <input
                    type="checkbox"
                    onChange={this.onCompetitiveChange}
                    defaultChecked={false}
                />
                <input
                    type="checkbox"
                    onChange={this.onUsesVCChange}
                    defaultChecked={false}
                />
            </div>
        )
    }
}

export default PlaystyleInput;