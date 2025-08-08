module.exports = {
    content: ["./src/**/*.{js,jsx,ts,tsx}", "./public/index.html"],
    darkMode: 'class',
    theme: {
      extend: {
        colors: {
          rosepink: '#D4A7A7',
        },
      },
      screens: {
        xs: "500px",
        sm: "640px",
        md: "768px",
        lg: "1024px",
        xl: "1280px",
      }
    },
    plugins: [
      require('tailwind-scrollbar-hide')
    ],
  };
  