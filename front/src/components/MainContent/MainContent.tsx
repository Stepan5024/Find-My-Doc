import { motion } from 'framer-motion';

export const MainContent = () => {
  return (
    <motion.main
      className="flex-1 p-4"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      transition={{ duration: 0.5 }}
    >
      <h1 className="text-2xl font-bold">Good afternoon!</h1>
      <div className="mt-4">
        <h2 className="text-xl font-semibold">Unstated Project</h2>
       
      </div>
      <button className="mt-4 bg-blue-500 text-white p-2 rounded hover:bg-blue-600">Explore templates</button>
    </motion.main>
  );
};