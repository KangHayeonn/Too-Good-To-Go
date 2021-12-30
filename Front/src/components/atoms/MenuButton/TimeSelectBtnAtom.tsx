import React from "react";
import styled from "@emotion/styled";

const Btn = styled.div`
	width: 100px;
	height: 70px;

	background: #e0e0e0;
	color: #999;
	font-weight: 600;
	font-size: 15px;

	cursor: pointer;
	display: flex;
	align-items: center;
	justify-content: center;

	border-radius: 2px;
	margin-right: 17.5px;
	margin-bottom: 17.5px;
	&:nth-child(3n) {
		margin-right: 0;
	}

	&: hover {
		background: #55b689;
		color: #fff;
	}
`;

type TSBtnType = {
	text: string;
	onClick: any;
};

const TimeSelectBtnAtom: React.FC<TSBtnType> = ({ text, onClick }) => {
	return <Btn onClick={onClick}>{text}</Btn>;
};

export default TimeSelectBtnAtom;
