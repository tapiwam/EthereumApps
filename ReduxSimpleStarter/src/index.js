import React from 'react';
import ReactDOM from 'react-dom';

// Create a new component. Should produce some HTML
const App = () => {
    // return <div>Hi!</div>;

    return <ol>
        <li>1</li>
        <li>2</li>
        <li>3</li>
        <li>4</li>
    </ol>
}

// Add components to DOM so user can view
ReactDOM.render(<App />, document.querySelector('.container'));
