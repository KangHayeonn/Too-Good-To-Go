import React from "react";
import styled from "@emotion/styled";
import { css } from "@emotion/react";
import { Link } from "react-router-dom";

const Wrap = styled.li`
	width: 258px;
	height: 258px;
	border: 1px solid #999;
	overflow: hidden;
	display: block;
	position: relative;
	cursor: pointer;
	margin-right: 46.66px;
	margin-bottom: 46.66px;
	background-color: #fff;
	&:nth-of-type(4n) {
		margin-right: 0;
	}
`;

const linkStyle = css`
	width: 258px;
	height: 258px;
	display: block;
`;

const Text = styled.p`
	position: absolute;
	top: 10%;
	left: 10%;
	font-size: 23px;
	color: #333;
`;

const Image = styled.img`
	position: absolute;
	bottom: 0;
	right: 0;
	width: 180px;
	float: right;
`;

type menuType = {
	to: string;
	menuName: string;
	image: string;
};

const MainMenuButton: React.FC<menuType> = ({ to, menuName, image }) => {
	return (
		<Wrap>
			<Link to={`/shopmenu/${to}`} css={linkStyle}>
				<Text>{menuName}</Text>
				<Image src={`${image}`} alt="사진ㅎㅎ" />
			</Link>
		</Wrap>
	);
};

export default MainMenuButton;
