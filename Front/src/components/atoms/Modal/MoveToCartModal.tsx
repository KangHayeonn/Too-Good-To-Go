import React, { useCallback } from "react";
import { useHistory } from "react-router-dom";
import styled from "@emotion/styled";

const Container = styled.div`
	background: rgba(0, 0, 0, 0.3);
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 15px;
	z-index: 2;
`;
const Popup = styled.div`
	width: 100%;
	max-width: 380px;
    max-height: 230px;
	border-radius: 10px;
	overflow: hidden;
	background: #6ec19b;
	box-shadow: 5px 10px 10px 1px rgba(0, 0, 0, 0.3);
`;
const Header = styled.div`
	width: 100%;
	height: 50px;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 19px;
	font-weight: bold;
	color: #404040;
`;
const Body = styled.div`
	width: 100%;
    height: 70px;
	background: #ffffff;
	display: flex;
	justify-content: center;
    padding-top: 30px;
	padding-bottom: 5px;
`;
const Footer = styled.div`
	width: 100%;
	display: inline-flex;
	height: 42px;
	justify-content: center;
	align-tems: center;
	float: left;
	color: #ffffff;
	cursor: pointer;
    margin-bottom: 9px;

	.pop-btn {
		width: 50%;
		height: 20px;
		display: flex;
		justify-content: center;
		align-items: center;
        background: #6ec19b;
        border-radius: 0px;
        font-weight: 500;
	}
	.pop-btn.confirm {
		border-right: 1px solid #d6e3dd; //오른쪽 줄
	}
`;
const MenuBox = styled.ul`
	width: 100%;
	height: 80%;
	min-height: 100px; //최소 높이
	max-height: 100px; //최대 높이
`;

type ModalProps = {
	setModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
};
const MoveToCartModal: React.FC<ModalProps> = ({ setModalOpen }) => {
    const history = useHistory();

	const onClickClosed = useCallback(() => {
		setModalOpen(false);
	}, []);

	return (
		<>
			<Container>
				<Popup>
					<Header>
						<span className="head-title">장바구니 담기완료</span>
					</Header>
					<Body>
						<div className="body-contentbox">
							<MenuBox>
								<li>장바구니에 담았습니다.</li>
                                <li>장바구니로 이동하시겠습니까?</li>
							</MenuBox>
						</div>
					</Body>
					<Footer>
						<button
							type="button"
							className="pop-btn confirm"
							id="confirm"
                            onClick={() => {
                                history.push("/cart");
                            }}
						>
							확인
						</button>
						<button
							type="button"
							className="pop-btn close"
							id="close"
							onClick={onClickClosed}
						>
							계속 쇼핑
						</button>
					</Footer>
				</Popup>
			</Container>
		</>
	);
};

export default MoveToCartModal;
