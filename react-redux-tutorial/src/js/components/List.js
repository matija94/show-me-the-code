import React from "react"
import { connect } from "react-redux"

const mapStateToProps = state => {
  return {
    articles: state.articles
  };
};

function ConnectedList(props) {
  return (
      <ul className="list-group list-group-flush">
        { props.articles.map(el => (
            <li className="list-group-item" key={el.id}>
              {el.title}
            </li>
        ))}
      </ul>
  )
}

/*
const ConnectedList = ({articles}) => (
  <ul className="list-group list-group-flush">
    { articles.map(el => (
      <li className="list-group-item" key={el.id}>
        {el.title}
      </li>
    ))}
  </ul>
);
*/

const List = connect(mapStateToProps)(ConnectedList);

export default List;