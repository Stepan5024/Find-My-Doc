import { motion } from 'framer-motion';

interface FooterProps {
  isSidebarCollapsed: boolean; // Добавляем пропс isSidebarCollapsed
}

export const Footer = ({ isSidebarCollapsed }: FooterProps) => {
  return (
    <motion.footer
      className="bg-green-100 shadow p-4 flex justify-between items-center" // Изменено на bg-green-100
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      transition={{ duration: 0.5 }}
      style={{ marginLeft: isSidebarCollapsed ? '4rem' : '16rem' }} // Настраиваем отступ в зависимости от состояния Sidebar
    >
      <p className="text-gray-600">4/250</p>
      <button className="bg-green-500 text-white p-2 rounded hover:bg-green-600">Upgrade</button>
    </motion.footer>
  );
};