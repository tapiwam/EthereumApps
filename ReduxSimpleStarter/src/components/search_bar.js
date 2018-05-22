import React, { Component } from 'react';

class SearchBar extends Component {

    render() {
        return <div>
                <label>Search </label>
                {/*<input onChange={ this.onInputChange } />*/}
                <input onChange={ event => console.log(event.target.value) } />
            </div>
    }

    onInputChange(event) {
        console.log(event.target.value);
    }
}


export default SearchBar;
