import React, { useCallback } from "react";
import styled from "@emotion/styled";
import CloseIcon from "@mui/icons-material/Close";

const Container = styled.div`
	background: rgba(0, 0, 0, 0.1);
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
	max-width: 333px;
	border-radius: 3px;
	overflow: hidden;
	background: #363636;
	box-shadow: 5px 10px 10px 1px rgba(0, 0, 0, 0.3);
`;
const Header = styled.div`
	width: 100%;
	height: 45px;
	display: flex;
	align-items: center;
	justify-content: center;
	justify-content: space-between;
	font-size: 16px;
	color: #fff;
	& > span {
		margin-left: 20px;
	}

	& > svg {
		margin-right: 20px;
		cursor: pointer;
	}
`;
const Body = styled.div`
	width: 100%;
	background: #ffffff;
	display: flex;
	justify-content: center;
	padding-top: 29px;
	padding-bottom: 29px;
`;

type ModalProps = {
	setModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
	errorMessage: string;
};
const LoginErrorModal: React.FC<ModalProps> = ({
	setModalOpen,
	errorMessage,
}) => {
	const onClickClosed = useCallback(() => {
		setModalOpen(false);
	}, []);
	return (
		<>
			<Container>
				<Popup>
					<Header>
						<span>로그인 실패</span>
						<CloseIcon onClick={onClickClosed} />
					</Header>
					<Body>{errorMessage}</Body>
				</Popup>
			</Container>
		</>
	);
};

export default LoginErrorModal;
