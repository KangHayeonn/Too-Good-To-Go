import React from "react";
import styled from "@emotion/styled";
import { css } from "@emotion/react";
import { Link } from "react-router-dom";

const Wrap = styled.li`
	width: 258px;
	height: 258px;
	overflow: hidden;
	display: block;
	position: relative;
	cursor: pointer;
	margin-right: 49.33px;
	margin-bottom: 49.33px;
	background-color: #fff;
	&:nth-of-type(4n) {
		margin-right: 0;
	}
	transition: all 0.3s ease-in-out;
	box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);
	&:hover {
		box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25),
			0 10px 10px rgba(0, 0, 0, 0.22);
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
	font-weight: 700;
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
