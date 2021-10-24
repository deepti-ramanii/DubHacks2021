import React, {Component} from "react";

interface HobbyProps {
    toggleLikesAnime(): void;
}

class HobbyInput extends Component<HobbyProps> {
    onLikesAnimeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.props.toggleLikesAnime();
    };

    render() {
        return (
            <div className="imagerow">
                <span>Like Anime: </span>
                <input
                    type="checkbox"
                    onChange={this.onLikesAnimeChange}
                    defaultChecked={false}
                />
            </div>
        )
    }
}

export default HobbyInput;