import React from "react";
import styled from "@emotion/styled";

type TsBtnStyleType = {
	bgColor: string;
	txtColor: string;
};

const Btn = styled.div<TsBtnStyleType>`
	width: 100px;
	height: 70px;

	background-color: ${(props: TsBtnStyleType) => props.bgColor};
	color: ${(props: TsBtnStyleType) => props.txtColor};
	font-weight: 600;
	font-size: 15px;

	cursor: pointer;
	display: flex;
	align-items: center;
	justify-content: center;

	border-radius: 2px;
	margin-right: 17.5px;
	margin-bottom: 17.5px;
	&:nth-of-type(3n) {
		margin-right: 0;
	}
	&:hover {
		background-color: #55b689;
		color: #fff;
	}
`;

type TSBtnType = {
	text: string;
	onClick: React.MouseEventHandler<HTMLElement>;
	background: string;
	textColor: string;
};

const TimeSelectBtnAtom: React.FC<TSBtnType> = ({
	text,
	onClick,
	background,
	textColor,
}) => {
	return (
		<Btn
			onClick={onClick}
			className="timeSelectBtn"
			bgColor={background}
			txtColor={textColor}
		>
			{text}
		</Btn>
	);
};

export default TimeSelectBtnAtom;
