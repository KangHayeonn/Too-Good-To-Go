import React from "react";
import styled from "@emotion/styled";
import "./index.css";

const Wrapper = styled.div`
	text-align: center;
	background-color: #3c3c3c;
	color: #fff;
	height: 10;
	width: 100%;
	border: 1px solid black;
`;
const MainBoard: React.FC = () => {
	return (
		<div className="address">
			<h4>앞으로 프로젝트 같이 잘해봐요! 🙂</h4>
		</div>
	);
};

export default MainBoard;
