import React, {Component} from "react";

interface PlaystylePrefProps {
    toggleCompetitivePref(): void;
    toggleUsesVCPref(): void;
}

class PlaystylePrefInput extends Component<PlaystylePrefProps> {
    onCompetitivePrefChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.props.toggleCompetitivePref();
    };

    onUsesVCPrefChange = (event: React.ChangeEvent<HTMLInputElement>)  => {
        this.props.toggleUsesVCPref();
    }

    render() {
        return (
            <div className="imagerow">
                <span>Competitive: </span>
                <input
                    type="checkbox"
                    name="Competitive: "
                    onChange={this.onCompetitivePrefChange}
                    defaultChecked={false}
                />
                <span> Use VC: </span>
                <input
                    type="checkbox"
                    name="Use VC: "
                    onChange={this.onUsesVCPrefChange}
                    defaultChecked={false}
                />
            </div>
        )
    }
}

export default PlaystylePrefInput;